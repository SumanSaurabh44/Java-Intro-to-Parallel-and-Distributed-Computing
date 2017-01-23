Main.java

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String name,password,register,login,message;
	public Main() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		name = request.getParameter("name");
		password = request.getParameter("password");
		register = request.getParameter("register");
		login = request.getParameter("login");
		if(check(name, password, response)){
			if(login != null)

				processLogin(request,response);
			else
				processRegister(request, response);
		}
	}

	//B. 
	private boolean check(String name, String password, ServletResponse response) {
		//three cases that you return false:
		// name and password are null.
		// name is not null but it is an empty string.
		// password is not null but it is an empty string.
		//Remove it when it is complete

		boolean returnType = false;
		try {
			PrintWriter pw = response.getWriter();
			// name and password are null.
			if (password.equals("") && name.equals("")) 
			{
				//To prompt "Sorry, the User-name and Password field can not be empty"
				pw.println("<html>\n" +
						"<head><title>Processing Cookies</title></head>\n" +
						"<body bgcolor=#7a7a7a>\n" +
						"<h1 align=\"center\">Sorry, The Username and Password field can not be empty<br></h1>\n" +
						"<h3 align=\"center\">To register or login, you must fill the username and password field.<br>Thank You...</h3>\n" +
						"</body></html>");
				returnType = true;
			}
			// name is not null but it is an empty string.
			if (password.equals("") && !name.equals("")) 
			{
				//To display "Sorry, you cannot register or login with EMPTY PASSWORD field"
				pw.println("<html>\n" +
						"<head><title>Processing Cookies</title></head>\n" +
						"<body bgcolor=#7a7a7a>\n" +
						"<h1 align=\"center\">Sorry, you cannot register or login with Empty Password field</h1>\n" +
						"<h3 align=\"center\">You must enter the Password.<br>Thank You.</h3>\n" +        				"</body></html>");
				returnType = true;
			}

			if (!password.equals("")&& name.equals(""))
				// password is not null but it is an empty string.
			{
				//To display message "Sorry, you cannot register or login with EMPTY USER NAME field"
				pw.println("<html>\n" +
						"<head><title>Processing Cookies</title></head>\n" +
						"<body bgcolor=#7a7a7a>\n" +
						"<h1 align=\"center\">Sorry, you cannot register or login with Empty User Name field</h1>\n" +
						"<h3 align=\"center\">You must enter the username field.<br>Thank You...</h3>\n" +
						"</body></html>");
				returnType = true;
			}		} catch (Exception ex) {
				ex.printStackTrace();
			}
		return !returnType;
	}
	//C.
	private void processRegister(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//there is no cookie. Make one.
		// One of cookies’ name is the same as the user name Tell user
		// you have already registered.
		//None of the cookies’ name is the same as the user name. Make one

		Cookie[] cookies = request.getCookies();
		response.setContentType("text/html");
		PrintWriter printWriterOut = response.getWriter();
		//To check that username and password fields are entered and it is not an empty
		if (cookies != null && cookies.length > 0)
			//check for the present cookies
			for(int i=0; i<cookies.length; i++)
			{
				Cookie c = cookies[i];
				if ((c.getName().equals(name)) && (c.getValue().equals(password)))//check the entered name and password with the already registered cookie
				{ 		
					//To display message that he is already registered
					printWriterOut.println("<html>\n" +
							"<head><title>Processing Cookies</title></head>\n" +
							"<body bgcolor=#7a7a7a>\n" +
							"<h1 align=\"center\">You are already registered. I have the cookies of your credentials.</h1>\n" +
							"<h3 align=\"center\">Please go ahead and login<br>Thank You</h3>\n" +
							"</body></html>");

					return;
				}
			}
		// new User registration
		//no cookies are available
		//Creating a new cookie for new user
		Cookie aCookie = new Cookie(name,password);
		aCookie.setMaxAge(60*60*24*365); //One year
		response.addCookie(aCookie);
		PrintWriter pwOut = response.getWriter();
		//To display message that he/she is registered
		pwOut.println("<html>\n" +
				"<head><title>Processing Cookies</title></head>\n" +
				"<body bgcolor=#7a7a7a>\n" +
				"<h1 align=\"center\">You are registered now....</h1>\n" +
				"<h3 align=\"center\">Your credential cookie is created in our Database<br>Directly login, When you visit next time.<br> Thank You</h3>\n" +
				"</body></html>");
	}
	//D.
	@SuppressWarnings("unused")
	private void makeCookie(HttpServletResponse response) throws IOException{
		// Make a cookie with its name and value to be the user name and
		// password respectively.
		Cookie aCookie = new Cookie(name,password);
		aCookie.setMaxAge(60*60*24*365); // 1 year
		response.addCookie(aCookie);

	}
	//E.
	private void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// No cookie at all. Ask the user to register.
		// No cookie’s name is the same as the user name. Ask the user
		// to register.
		// One cookie’s name is the same as the user name. Display
		// a welcome back message

		Cookie[] cookies = request.getCookies();
		response.setContentType("text/html");
		//PrintWriter p = response.getWriter();
		if (cookies != null && cookies.length > 0)
			//check if cookies are available
			//check for the availability of cookie for the entered user-name and password 
			for(int i=0; i<cookies.length; i++) {
				//Scanning cookies
				Cookie c = cookies[i];
				PrintWriter printWriterOut = response.getWriter();

				if ((c.getName().equals(name)) && (c.getValue().equals(password)))
					//if entered name and password matches with the alreaddy present cookie then ok.
				{
					//Let Username know that he is logged in
					printWriterOut.print("<html>\n" +
							"<head><title>Processing Cookies</title></head>\n" +
							"<body bgcolor=#7a7a7a>\n" +
							"<h1 align=\"center\">Welcome Back Home...</h1>\n" +
							"<h3 align=\"center\">Login Successfully...</h3>\n" +

							"</body></html>");
					return;
				}
			}
		else
			//if entered username and password doesnot match with the alreaady present cookie
			//then To display message that he is not registered
			response.setContentType("text/html");
		PrintWriter pwOut = response.getWriter();
		pwOut.print("");
		//let username know that he is not yet registered
		pwOut.println("<html>\n" +
				"<head><title>Processing Cookies</title></head>\n" +
				"<body bgcolor=#7a7a7a>\n" +
				"<h2 align=\"center\">Sorry</h1>\n" +
				"<h2 align=\"center\">Their are no cookies available with your User Name and Password<br></h2>\n" +
				"<h2 align=\"center\">Please register first...<br> Thank You...</h2>\n" +

				"</body></html>");
	}
}

