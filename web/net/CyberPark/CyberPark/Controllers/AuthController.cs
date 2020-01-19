using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Runtime.Serialization.Formatters.Binary;
using System.Collections;
using System.Web;
using System.IO;


namespace CyberPark.Controllers
{
  public class AuthController : ApiController
  {
    public IEnumerable<string> GetAllAuth()
    {
      // use binary formatter for serialization
      BinaryFormatter formatter = new BinaryFormatter();

      // is there Authorization header?
      if (Request.Headers.Contains("Authorization"))
      {
        // get the header's value and base64-decode it
        byte[] authBytes = Convert.FromBase64String(Request.Headers.GetValues("Authorization").Single());
        var memoryStream = new MemoryStream(authBytes);

        // totally safe LOL
        string[] dummy = (string[])formatter.Deserialize(memoryStream);

        return dummy;
      }

      else
      {
        // just return something to the user
        string[] dummy = new string[] { "foo", "bar" };
        var memoryStream = new MemoryStream();

        // serialize dummy object
        formatter.Serialize(memoryStream, dummy);

        // base64-encode the serialized data and return
        return new string[] { "Sample Authorization value", Convert.ToBase64String(memoryStream.ToArray()) };
      }

    }
  }
}
