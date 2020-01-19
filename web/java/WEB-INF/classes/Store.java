import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Store extends HttpServlet {

  // the store front
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/store.jsp").forward(request, response);
  }

  // handle the sale request
  public void doPost(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    // create input stream from the HTTP parameter
    Part filePart   = request.getPart("items");
    InputStream is = filePart.getInputStream();

    try {
      // ObjectInputStream so we could deserialize
      ObjectInputStream ois = new ObjectInputStream(is);
      
      // actual deserialization; super safe LOL :sarcasm:
      Items items = (Items) ois.readObject();
      
      // close the streams
      ois.close();
      is.close();

      // generate a response; should I fix the XSS? ;)
      request.setAttribute("message", "Just sold " + items);
      request.getRequestDispatcher("/WEB-INF/store.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace(out);
    }
  }

}
