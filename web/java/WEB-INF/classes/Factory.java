import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Factory extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/factory.jsp").forward(request, response);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {

      String  name = request.getParameter("name");
      Integer quantity = Integer.parseInt(request.getParameter("quantity"));

      Items items = new Items(name, quantity);

      response.setContentType("application/x-binary");
      response.setHeader("Content-Disposition", "attachment; filename=" + name + "s.ser");

      OutputStream rout       = response.getOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(rout);
      oout.writeObject(items);   // serialization
      oout.close();
      rout.close();
   }

}
