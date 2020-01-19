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
      BinaryFormatter formatter = new BinaryFormatter();

      if (Request.Headers.Contains("Authorization"))
      {
        byte[] authBytes = Convert.FromBase64String(Request.Headers.GetValues("Authorization").Single());

        var memoryStream = new MemoryStream(authBytes);
        string[] dummy = (string []) formatter.Deserialize(memoryStream); // totally safe LOL

        return dummy;
      }
      else
      {
        string[] dummy = new string[] { "the", "best" };

        var memoryStream = new MemoryStream();
        formatter.Serialize(memoryStream, dummy);

        return new string[] { "Sample Authorization value", Convert.ToBase64String(memoryStream.ToArray()) };
      }

    }
  }
}
