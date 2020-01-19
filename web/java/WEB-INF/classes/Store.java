import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Store extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/store.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    Part filePart   = request.getPart("items");
    InputStream fin = filePart.getInputStream();

    try {
      ObjectInputStream oin = new ObjectInputStream(fin);
      Items items = (Items) oin.readObject();   // deserialization
      oin.close();
      fin.close();

      request.setAttribute("message", "Just sold " + items);
      request.getRequestDispatcher("/WEB-INF/store.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace(out);
    }
  }

}
