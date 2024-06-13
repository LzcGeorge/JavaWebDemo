package user.dao;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import user.domain.User;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 数据类
 */

public class UserDao {
    private String path = "/Users/selfknow/program/Heima/code/User/src/users.xml";

    public User findByUsername(String username) {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(path);
            // 通过 xpath 查询得到 Element
            Element ele = (Element) doc.selectSingleNode("//user[@username='" + username + "']");

            if(ele == null) return null;

            // 封装到 User 对象
            User user = new User();
            String name = ele.attributeValue("username");
            String pwd = ele.attributeValue("password");
            user.setUsername(name);
            user.setPassword(pwd);

            return user;
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(path);
            Element root = doc.getRootElement(); // <users>

            // 通过root 创建新元素
            Element userEle = root.addElement("user");
            userEle.addAttribute("username",user.getUsername());
            userEle.addAttribute("password",user.getPassword());

            OutputFormat format = new OutputFormat("\t",true); // 缩进和换行
            format.setTrimText(true); // 清空原有的换行和缩进

            XMLWriter xmlWriter = new XMLWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path),"UTF-8"),format);
            xmlWriter.write(doc);
            xmlWriter.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
