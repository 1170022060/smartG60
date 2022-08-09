package com.ruoyi.common.core.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceUtils {

    //解析xml
    public static String get(String host) throws ParserConfigurationException, IOException, SAXException {
        InputStream is = null;
        // 创建远程url连接对象
        URL url = new URL(host);
        // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置连接方式：get
        httpURLConnection.setRequestMethod("GET");
        //设置连接主机服务器的超时时间：15000毫秒
        httpURLConnection.setConnectTimeout(15000);
        // 设置读取远程返回的数据时间：60000毫秒
        httpURLConnection.setReadTimeout(60000);
        //发送请求
        httpURLConnection.connect();
        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document document;
        NodeList nl;
        StringBuffer sb = null;
        Node n;
        //通过connection获取输入流
        if (httpURLConnection.getResponseCode() == 200) {
            //获取输入流
            is = httpURLConnection.getInputStream();
            //调用 DocumentBuilderFactory.newInstance() 方法得到创建 DOM 解析器的工厂。
            dbf = DocumentBuilderFactory.newInstance();
            //调用工厂对象的 newDocumentBuilder方法得到 DOM 解析器对象。
            db = dbf.newDocumentBuilder();
            //调用 DOM 解析器对象的 parse() 方法解析 XML 文档，得到代表整个文档的 Document 对象，进行可以利用DOM特性对整个XML文档进行操作了
            document = (Document) db.parse(is);
            nl = document.getElementsByTagName("string");
            //封装成缓存流
            sb = new StringBuffer();
            for (int count = 0; count < nl.getLength(); count++) {
                n = nl.item(count);
                sb.append(n.getFirstChild().getNodeValue());
            }
            is.close();
        }
        return sb.toString();
    }
}
