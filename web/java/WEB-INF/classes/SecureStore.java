import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SecureStore extends HttpServlet {

  // the store front
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/securestore.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    Part filePart   = request.getPart("items");
    InputStream is = filePart.getInputStream();

    try {
      // use something better than plain ObjectInputStream
      SafeObjectInputStream sois = new SafeObjectInputStream(is);

      // actual deserialization; actually safe this time
      Items items = (Items) sois.readObject();
      
      // close the streams
      sois.close();
      is.close();

      // generate a response; should I fix the XSS? ;)
      request.setAttribute("message", "Just sold " + items);
      request.getRequestDispatcher("/WEB-INF/securestore.jsp").forward(request, response);

    } catch (InvalidClassException e) {
      // invalid class? let the user know!
      request.setAttribute("message", "Sorry, we do not accept " + e.classname);
      request.getRequestDispatcher("/WEB-INF/securestore.jsp").forward(request, response);

    } catch (Exception e) {
      e.printStackTrace(out);
    }
  }

  
  private class SafeObjectInputStream extends ObjectInputStream {

    public SafeObjectInputStream(InputStream inputStream) throws IOException {
      super(inputStream);
    }

    // look-ahead deserialization
    @Override
    protected Class<?> resolveClass(ObjectStreamClass input)
                                    throws IOException, ClassNotFoundException
    {
      // is the submitted class the one we expect?
      if (!input.getName().equals(Items.class.getName())) {
        throw new InvalidClassException(input.getName(), "Unsupported class");
      }
      return super.resolveClass(input);
    }
  }

}
