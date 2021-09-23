package com.yansir.generator;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by hevenzheng on 2017/2/8.
 */
//@Slf4j
public class MySQLLimitPlugin extends PluginAdapter {

    private static String XMLFILE_POSTFIX = "Ext";

    private static String JAVAFILE_POTFIX = "Ext";

    private static String ANNOTATION_RESOURCE = "javax.annotation.Resource";

    private static String ANNOTATION_REPOSITORY = "org.springframework.stereotype.Repository";

    private static String ANNOTATION_BUILDER = "lombok.Builder";

    private static String ANNOTATION_NOARGSCONSTRUCTOR = "lombok.NoArgsConstructor";

    private static String ANNOTATION_ALLARGSCONSTRUCTOR = "lombok.AllArgsConstructor";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    /**
    * @author YANSIR
    * @Description:添加表字段注释
    * @date 2019/6/21 16:42
    */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * " + introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
        return true;
    }

    /**
    * @author YANSIR
    * @Description:添加@Build注解
    * @date 2019/6/21 15:44
    */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**");
        sb.append(" * YANSIR");
        sb.append("\n * " + LocalDateTime.now());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addAnnotation("@AllArgsConstructor");
        topLevelClass.addImportedType(new FullyQualifiedJavaType(ANNOTATION_ALLARGSCONSTRUCTOR));
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addImportedType(new FullyQualifiedJavaType(ANNOTATION_NOARGSCONSTRUCTOR));
        topLevelClass.addAnnotation("@Builder");
        topLevelClass.addImportedType(new FullyQualifiedJavaType(ANNOTATION_BUILDER));
        List<Method> methods = topLevelClass.getMethods();
        List<Method> remove = new ArrayList();
        Iterator i$ = methods.iterator();
        while(i$.hasNext()) {
            Method method = (Method)i$.next();
            if (method.getBodyLines().size() < 2) {
                remove.add(method);
                System.out.println("-----------------" + topLevelClass.getType().getShortName() + "'s method=" + method.getName() + " removed cause lombok annotation.");
            }
        }

        methods.removeAll(remove);
        return true;
    }

    /**
     * 为每个Example类添加limit和offset属性已经set、get方法
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        Field limit = new Field();
        limit.setName("limit");
        limit.setVisibility(JavaVisibility.PRIVATE);
        limit.setType(integerWrapper);
        topLevelClass.addField(limit);

        Method setLimit = new Method();
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.setName("setLimit");
        setLimit.addParameter(new Parameter(integerWrapper, "limit"));
        setLimit.addBodyLine("this.limit = limit;");
        topLevelClass.addMethod(setLimit);

        Method getLimit = new Method();
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerWrapper);
        getLimit.setName("getLimit");
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);

        Field offset = new Field();
        offset.setName("offset");
        offset.setVisibility(JavaVisibility.PRIVATE);
        offset.setType(integerWrapper);
        topLevelClass.addField(offset);

        Method setOffset = new Method();
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.setName("setOffset");
        setOffset.addParameter(new Parameter(integerWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);

        Method getOffset = new Method();
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerWrapper);
        getOffset.setName("getOffset");
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);

        return true;
    }

    // 添删改Document的sql语句及属性
    @Override
    public boolean sqlMapDocumentGenerated(Document document,
                                           IntrospectedTable introspectedTable) {

        XmlElement parentElement = document.getRootElement();

        updateDocumentNameSpace(introspectedTable, parentElement);

//        moveDocumentInsertSql(parentElement);

//        updateDocumentInsertSelective(parentElement);
//
//        moveDocumentUpdateByPrimaryKeySql(parentElement);
//
//        generateMysqlPageSql(parentElement, introspectedTable);
//
//        generateDataAccessSql(parentElement);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private void updateDocumentNameSpace(IntrospectedTable introspectedTable,
                                         XmlElement parentElement) {
        Attribute namespaceAttribute = null;
        for (Attribute attribute : parentElement.getAttributes()) {
            if (attribute.getName().equals("namespace")) {
                namespaceAttribute = attribute;
            }
        }
        parentElement.getAttributes().remove(namespaceAttribute);
        parentElement.getAttributes().add(
                new Attribute("namespace", introspectedTable
                        .getMyBatis3JavaMapperType() + JAVAFILE_POTFIX));
    }

    /**
     * 为Mapper.xml的selectByExample添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {

        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

        XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "offset != null"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${offset}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "offset == null"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);

        return true;
    }

    // 生成XXExt.xml
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
            IntrospectedTable introspectedTable) {

        String[] splitFile = introspectedTable.getMyBatis3XmlMapperFileName()
                .split("\\.");
        String fileNameExt = null;
        if (splitFile[0] != null) {
            fileNameExt = splitFile[0] + XMLFILE_POSTFIX + ".xml";
        }

        if (isExistExtFile(context.getSqlMapGeneratorConfiguration()
                        .getTargetProject(),
                introspectedTable.getMyBatis3XmlMapperPackage(), fileNameExt)) {
            return super.contextGenerateAdditionalXmlFiles(introspectedTable);
        }

        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);

        XmlElement root = new XmlElement("mapper");
        document.setRootElement(root);
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace()
                + XMLFILE_POSTFIX;
        root.addAttribute(new Attribute("namespace", namespace));

        GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileNameExt,
                introspectedTable.getMyBatis3XmlMapperPackage(), context
                .getSqlMapGeneratorConfiguration().getTargetProject(),
                false, context.getXmlFormatter());

        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);
        answer.add(gxf);

        return answer;
    }

    // 生成XXExt.java
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
            IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType() + JAVAFILE_POTFIX);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(interfaze);

        FullyQualifiedJavaType baseInterfaze = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType());
        interfaze.addSuperInterface(baseInterfaze);

        /*FullyQualifiedJavaType resourceAnnotation = new FullyQualifiedJavaType(
                ANNOTATION_RESOURCE);
        interfaze.addAnnotation("@Resource");
        interfaze.addImportedType(resourceAnnotation);*/
        FullyQualifiedJavaType reposiryeAnnotation = new FullyQualifiedJavaType(
                ANNOTATION_REPOSITORY);
        interfaze.addAnnotation("@Repository");
        interfaze.addImportedType(reposiryeAnnotation);

        CompilationUnit compilationUnits = interfaze;
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(
                compilationUnits,
                context.getJavaModelGeneratorConfiguration().getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());

        if (isExistExtFile(generatedJavaFile.getTargetProject(),
                generatedJavaFile.getTargetPackage(),
                generatedJavaFile.getFileName())) {
            return super.contextGenerateAdditionalJavaFiles(introspectedTable);
        }
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<GeneratedJavaFile>(
                1);
        generatedJavaFile.getFileName();
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    private boolean isExistExtFile(String targetProject, String targetPackage,
                                   String fileName) {

        File project = new File(targetProject);
        if (!project.isDirectory()) {
            return true;
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, ".");
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                return true;
            }
        }

        File testFile = new File(directory, fileName);
        if (testFile.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void updateDocumentInsertSelective(XmlElement parentElement) {
        List<XmlElement> changeList = new ArrayList<>();

        for (Element element : parentElement.getElements()) {
            XmlElement xmlElement = (XmlElement) element;
            if (xmlElement.getName().equals("insert")) {
                changeList.add(xmlElement);
            }

        }

        for(XmlElement xe : changeList) {
            xe.addAttribute(new Attribute(
                    "useGeneratedKeys", "true"));
            xe.addAttribute(new Attribute("keyProperty",
                    "id"));
            XmlElement selectKeyElement = new XmlElement("selectKey");
            selectKeyElement.addAttribute(new Attribute("keyProperty", "id"));
            selectKeyElement.addAttribute(new Attribute("order", "BEFORE"));
            selectKeyElement.addAttribute(new Attribute("resultType", "java.lang.String"));
            selectKeyElement.addElement(new TextElement("SELECT UUID()"));
            xe.addElement(0, selectKeyElement);
        }

    }

    private void moveDocumentInsertSql(XmlElement parentElement) {
        XmlElement insertElement = null;
        for (Element element : parentElement.getElements()) {
            XmlElement xmlElement = (XmlElement) element;
            if (xmlElement.getName().equals("insert")) {
                for (Attribute attribute : xmlElement.getAttributes()) {
                    if (attribute.getValue().equals("insert")) {
                        insertElement = xmlElement;
                        break;
                    }
                }
            }
        }
        parentElement.getElements().remove(insertElement);
    }
}
