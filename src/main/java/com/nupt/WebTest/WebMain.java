package com.nupt.WebTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginContext;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebMain {
	private static String url = "http://blog.csdn.net";
	private static String blogName = "guoxiaolongonly";

public static void main(String[] args) throws Exception{

	login();
	}


public static void getArticleListFromUrl(String listurl) {
	Document doc = null;
	try {
		doc = Jsoup.connect(listurl).userAgent("Mozilla/5.0").timeout(3000).post();
	} catch (IOException e) {
		e.printStackTrace();
	}
	// System.out.println(doc);
	Elements elements = doc.getElementsByTag("a");//找到所有a标签
	for (Element element : elements) {
		String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
		String linkHref = element.text();
		 //用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
		if (!relHref.startsWith("http://") && relHref.contains("details") && relHref.endsWith("comments"))
		{
			StringBuffer sb = new StringBuffer();
			sb.append(url).append(relHref);
			System.out.println(sb.substring(0, sb.length() - 9));//去掉最后的#comment输出
		}
//		System.out.println(linkHref);
		if(linkHref.equals("下一页"))//如果有下一页
		{
			getArticleListFromUrl(url + relHref);//获取下一页的列表
		}
	}
	
}


	public static void getArticleFromUrl(String detailurl) {
		try {
			Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0").timeout(3000).post();
			Element elementTitle = document.getElementsByClass("link_title").first();//标题。 这边根据class的内容来过滤
			System.out.println(elementTitle.text());
			String filename = elementTitle.text().replaceAll("/", "或");
			Element elementContent = document.getElementsByClass("article_content").first();//内容。
			System.out.println(elementContent.text());
			// String Content =elementContent.te  xt().replaceAll(" ", "\t");
			// System.out.println(elementContent.text()+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public static void login() throws Exception {
		// 第一次请求
		Connection con = Jsoup
				.connect("http://192.168.168.168/0.htm");// 获取连接
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
		Response rs = con.execute();// 获取响应
		Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
		List<Element> et = d1.select("div").get(1).select("form");// 获取form表单，可以通过查看页面源码代码得知
		// 获取，cooking和表单属性，下面map存放post时的数据
		Map<String, String> datas = new HashMap();
		for (Element e : et.get(0).getAllElements()) {
			if (e.attr("name").equals("DDDDD")) {
				e.attr("value", "101002005001900");// 设置用户名
			}
			if (e.attr("name").equals("upass")) {
				e.attr("value", "lxj140729"); // 设置用户密码
			}
			if (e.attr("name").length() > 0) {// 排除空值表单属性
				datas.put(e.attr("name"), e.attr("value"));
			}
		}
		/**
		 * 第二次请求，post表单数据，以及cookie信息
		 * 
		 * **/
		Connection con2 = Jsoup
				.connect("http://192.168.168.168/0.htm");
		con2.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).method(Method.POST)
				.data(datas).cookies(rs.cookies()).execute();
		// 打印，登陆成功后的信息
		System.out.println(login.body());
		// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
		Map<String, String> map = login.cookies();
		for (String s : map.keySet()) {
			System.out.println(s + "      " + map.get(s));
		}
	}


	
}
