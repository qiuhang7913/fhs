package com.self.framework.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.self.framework.generator.StaticConfg.hand;

/**
 * @des 代码生成器核心启动类
 * @author qiuhang
 * @version v1.0
 */
public class CoreMain {

    //================================================================================
    public static void main(String[] args) throws Exception {
        new PropertiesInit();//初始化加载配置文件
        new CoreMain().start();
    }



    // ===============================================================================
    public void start() throws Exception {
        // 获取当前文件绝对路径
        // 生成包名
        // 注 : 此格式只适用于idea
//        File f = new File(this.getClass().getResource("").getPath());
//        String packageName = f.getPath().split("classes\\\\")[1].replace("\\", ".");
        String packageName = StaticConfg.packageName;
//        // path为当前文件绝对路径
//        String path = f.getPath().replace("target\\classes", "src\\main\\java");
//        // 如果路径中有空格,idea会转成%,所以需要转回来
//        path = path.replace("%", " ");
        String path = StaticConfg.path;

        Class.forName(StaticConfg.driver);
        Connection conn = DriverManager.getConnection(StaticConfg.url, StaticConfg.user, StaticConfg.password);
        // 获取所有表名
        List<String> tables = StaticConfg.appoint;

        // 遍历所有需要获取的表
        for (String tableName : tables) {
            // 生成类名
            String ClassName = underline2Camel(hand != "" && tableName.startsWith(hand) ? tableName.substring(hand.length(), tableName.length()) : tableName, false);
            // 生成主键名
            String primarykey = null;
            // 主键类型
            String PrimaryKeyType = null;

            // 查询, 匹配主键名
            ResultSet resultSet = conn.getMetaData().getColumns(null, null, tableName.toUpperCase(), "%");
            ResultSet rs = conn.getMetaData().getPrimaryKeys(conn.getCatalog().toUpperCase(),
                    null, tableName.toUpperCase());
            while (rs.next()) {
                primarykey = rs.getString("COLUMN_NAME");
            }

            // 生成domain
            // 拼接文件内容
            StringBuffer sb = new StringBuffer();

            String str = domainMould;
            str = str.replace("domainPackageName", packageName + "." + StaticConfg.domainDir);
            str = str.replace("tableName", tableName);
            str = str.replace("ClassName", ClassName + "Bean");

            sb.append(str);
            System.out.println("\n表 " + tableName + " 生成类" + ClassName + "Bean");

            // 获取列名
            while (resultSet.next()) {
                // resultSet数据下标从1开始
                sb.append("\r\n");
                String columnName = resultSet.getString("COLUMN_NAME");
                String typeName = StaticConfg.map.get(resultSet.getString("TYPE_NAME"));
                String remarks = resultSet.getString("REMARKS");
                if (columnName.equals(primarykey)) {
                    sb.append("\r\n\t@Id");
                    sb.append("\r\n\t@GenericGenerator(name = \"user-uuid\", strategy = \"uuid\")");
                    sb.append("\r\n\t@GeneratedValue(generator = \"user-uuid\")");
                    PrimaryKeyType = typeName;
                }

                if (remarks != null && !"".equals(remarks)) {
                    sb.append("\r\n\t// " + remarks);
                }
                sb.append("\r\n\t@Column(name = \"" + columnName + "\")");
                if (typeName != null) {
                    System.out.print(columnName + "字段为" + typeName + "类型 " + "\t");
                    // 时间类型加上时间格式化
                    if (typeName.equals("Date")) {
                        sb.append("\r\n\t@DateTimeFormat(pattern = \"" + StaticConfg.pattern + "\")");
                    }
                    sb.append("\r\n\tprivate " + typeName + " " + underline2Camel(columnName, StaticConfg.ishump, StaticConfg.ishump) + ";");
                } else {
                    System.out.println("\033[31;4m" + "未知类型 " + columnName + "数据库类型为" + resultSet.getString("TYPE_NAME") + "\033[0m");
                    sb.append("\r\n\tprivate Object " + underline2Camel(columnName, StaticConfg.ishump, StaticConfg.ishump) + ";");
                }
            }

            sb.append("\r\n}");
            System.out.println();
            // 生成文件
            if (StaticConfg.isdomain) {
                String filePath = path + "/" + StaticConfg.packageName.replace(".", "/") + "/" + StaticConfg.domainDir;
                File file = new File(filePath, ClassName + "Bean.java");
                System.out.println("生成domain文件\t" + path + "/" + ClassName + "Bean.java");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(sb.toString().getBytes());
                outputStream.close();
            }

            // 生成dao
            if (StaticConfg.isdao) {
                File file2 = new File(path + "/" + StaticConfg.daomkdir, ClassName + "Dao.java");
                file2.mkdirs();
                //System.out.println("生成mapper文件\t" + path + "/" + ClassName + "Mapper.java");
                if (file2.exists()) {
                    file2.delete();
                }
                StringBuffer sbdao = new StringBuffer();
                sbdao.append("package " + packageName + "." + StaticConfg.daomkdir + ";\r\n\r\n");
                sbdao.append("import com.self.framework.base.BaseDao;\n");
                sbdao.append("import " + packageName + "." + ClassName + ";\n");
                sbdao.append("public interface " + ClassName + "Dao extends BaseDao<" + ClassName + "> {}\n");
                FileOutputStream outputStream2 = new FileOutputStream(file2);
                outputStream2.write(sbdao.toString().getBytes());
                outputStream2.close();
            }

            // 生成service   如果没有主键, 不生成service
            if (StaticConfg.isservice && PrimaryKeyType != null) {
                File file3 = new File(path + "/" + StaticConfg.servicemkdir, ClassName + "Service.java");
                file3.mkdirs();
                System.out.println("生成service文件\t" + path + "/" + ClassName + "Service.java");
                if (file3.exists()) {
                    file3.delete();
                }
                String MapperName = ClassName + "Dao";
                String mapperName = underline2Camel(ClassName) + "Dao";
                String className = underline2Camel(ClassName);

                StringBuffer sbservice = new StringBuffer();
                String s = serviceMould;
                s = s.replace("servicePackageName", packageName + "." + StaticConfg.servicemkdir);
                s = s.replace("domainPackageName", packageName + "." + ClassName);
                s = s.replace("mapperPackageName", packageName + "." + StaticConfg.daomkdir + "." + MapperName);
                s = s.replace("ServiceName", ClassName + "Service");
                s = s.replace("MapperName", MapperName);
                s = s.replace("mapperName", mapperName);
                s = s.replace("ClassName", ClassName);
                s = s.replace("className", className);
                s = s.replace("PrimaryKeyType", PrimaryKeyType);
                sbservice.append(s);
                FileOutputStream outputStream3 = new FileOutputStream(file3);
                outputStream3.write(sbservice.toString().getBytes());
                outputStream3.close();
            }
        }
    }

    // 获取所有表名
    private static ArrayList<String> getTables(Connection conn) throws SQLException {
        DatabaseMetaData dbMetData = conn.getMetaData();
        ResultSet rs = dbMetData.getTables(null, convertDatabaseCharsetType("root", "mysql"), null, new String[]{"TABLE", "VIEW"});
        ArrayList<String> tableNames = new ArrayList<String>();
        while (rs.next()) {
            if (rs.getString(4) != null
                    && (rs.getString(4).equalsIgnoreCase("TABLE") || rs
                    .getString(4).equalsIgnoreCase("VIEW"))) {
                String tableName = rs.getString(3).toLowerCase();
                tableNames.add(tableName);
            }
        }
        return tableNames;
    }

    public static String convertDatabaseCharsetType(String in, String type) {
        String dbUser;
        if (in != null) {
            if (type.equals("oracle")) {
                dbUser = in.toUpperCase();
            } else if (type.equals("postgresql")) {
                dbUser = "public";
            } else if (type.equals("mysql")) {
                dbUser = null;
            } else if (type.equals("mssqlserver")) {
                dbUser = null;
            } else if (type.equals("db2")) {
                dbUser = in.toUpperCase();
            } else {
                dbUser = in;
            }
        } else {
            dbUser = "public";
        }
        return dbUser;
    }

    /**
     * 下划线转驼峰法(默认小驼峰)
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean... smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);

        if (smallCamel.length == 2 && !smallCamel[0] && !smallCamel[1]){
            return line;
        }
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    // 高级配置, 自定义生成的service, domain和自段对应的类型
    //===============================================================================
    // 生成service的模板
    private final String serviceMould = "" +
            "package servicePackageName;\n" +
            "\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "import javax.annotation.Resource;\n" +
            "import java.util.List;\n" +
            "\n" +
            "import domainPackageName;\n" +
            "import mapperPackageName;\n" +
            "\n" +
            "@Service\n" +
            "@Transactional\n" +
            "public class ServiceName {\n" +
            "\n" +
            "    @Resource\n" +
            "    private MapperName mapperName;\n" +
            "\n" +
            "    /**\n" +
            "     * 添加\n" +
            "     * @param className\n" +
            "     */\n" +
            "    public void save(ClassName className){\n" +
            "        mapperName.insert(className);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 主键删除\n" +
            "     * @param id\n" +
            "     */\n" +
            "    public void delete(PrimaryKeyType id){\n" +
            "        mapperName.deleteByPrimaryKey(id);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 修改\n" +
            "     * @param className\n" +
            "     */\n" +
            "    public void update(ClassName className){\n" +
            "        mapperName.updateByPrimaryKey(className);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 查询所有\n" +
            "     * @return\n" +
            "     */\n" +
            "    public List<ClassName> findAll(){\n" +
            "        return mapperName.selectAll();\n" +
            "    }" +
            "\n" +
            "    /**\n" +
            "     * 查询\n" +
            "     * @return\n" +
            "     */\n" +
            "    public ClassName findById(PrimaryKeyType id){\n" +
            "        return mapperName.selectByPrimaryKey(id);\n" +
            "    }\n" +
            "}";

    // 生成domain的模板
    private final String domainMould = "" +
            "package domainPackageName;\n" +
            "\n" +
            "import lombok.*;\n" +
            "import org.hibernate.annotations.GenericGenerator;\n" +
            "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n" +
            "import com.self.framework.base.BaseBean;\n" +
            "import javax.persistence.*;\n" +
            "import java.sql.Timestamp;\n" +
            "import java.util.Date;\n" +
            "\n" +
            "@Data\n" +
            "@Entity\n" +
            "@Builder\n" +
            "@Table(name = \"tableName\")\n" +
            "@JsonIgnoreProperties(value = { \"hibernateLazyInitializer\", \"handler\" })\n" +
            "public class ClassName extends BaseBean{";
}
