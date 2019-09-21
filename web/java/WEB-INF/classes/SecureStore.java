import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SecureStore extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/securestore.jsp").forward(request, response);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {
      PrintWriter out = response.getWriter();

      Part filePart   = request.getPart("items");
      InputStream fin = filePart.getInputStream();

      try {
         // use a new class instead of ObjectInputStream:
         SafeObjectInputStream oin = new SafeObjectInputStream(fin);

         Items items = (Items) oin.readObject();   // deserialization
         oin.close();
         fin.close();

         request.setAttribute("message", "Just sold " + items);
         request.getRequestDispatcher("/WEB-INF/securestore.jsp").forward(request, response);

      } catch (InvalidClassException e) {
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

     @Override
     protected Class<?> resolveClass(ObjectStreamClass input)
                                     throws IOException, ClassNotFoundException
      {
         if (!input.getName().equals(Items.class.getName())) {
            throw new InvalidClassException(input.getName(), "Unsupported class");
         }
         return super.resolveClass(input);
      }
   }

}
