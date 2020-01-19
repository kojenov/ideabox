import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Factory extends HttpServlet {

  // display the product order form
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/factory.jsp").forward(request, response);
  }

  // process the product order
  public void doPost(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {

    String  name     = request.getParameter("name");
    Integer quantity = Integer.parseInt(request.getParameter("quantity"));

    // create an Items object with requested name and quantity
    Items items = new Items(name, quantity);

    // necessary headers
    response.setContentType("application/x-binary");
    response.setHeader("Content-Disposition", "attachment; filename=" + name + "s.ser");

    // output to HTTP response
    OutputStream       os  = response.getOutputStream();
    
    // ObjectOutputStream so we could serialize
    ObjectOutputStream oos = new ObjectOutputStream(os);
    
    // actual serialization
    oos.writeObject(items);
    
    // close and exit
    oos.close();
    os.close();
  }

}
