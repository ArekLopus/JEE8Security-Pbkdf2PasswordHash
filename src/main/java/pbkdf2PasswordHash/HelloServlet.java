package pbkdf2PasswordHash;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//-Params:
// • Pbkdf2PasswordHash.Algorithm		// default "PBKDF2WithHmacSHA256"
// • Pbkdf2PasswordHash.Iterations		// default 2048, minimum 1024
// • Pbkdf2PasswordHash.SaltSizeBytes	// default 32, minimum 16
// • Pbkdf2PasswordHash.KeySizeBytes	// default 32, minimum 16
@WebServlet("/servlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	Pbkdf2PasswordHash pbkdf2Hash;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<h3>Pbkdf2PasswordHash interface test</h3>");
		
		char[] pass = "abc".toCharArray();
		
		Map<String, String> params = new HashMap<>();
		params.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
		params.put("Pbkdf2PasswordHash.Iterations", "5000");
		params.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
		params.put("Pbkdf2PasswordHash.KeySizeBytes", "64");
		
		this.pbkdf2Hash.initialize(params);
		
		String str1 = this.pbkdf2Hash.generate(pass);
		pw.println(str1);
		boolean verified = this.pbkdf2Hash.verify(pass, str1);
		pw.println("<br/>pbkdf2Hash.verify -> " + verified);

	}
}
