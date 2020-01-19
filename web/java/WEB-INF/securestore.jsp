<!DOCTYPE html>
<html>
<head><title>Secure Store</title></head>
<body>
  <center>
    <h3>Secure Store</h3>
    <p><i>We sell all the things (securely)!</i></p>
    <form action="SecureStore" method=POST enctype="multipart/form-data">
      <p><input type=file name=items accept=".ser"></p>
      <input type=submit value="Sell!">
    </form>
    <p><i>${message}</i></p>
  </center>
<div style="height:100%; width:100%;">
  <img src="images/secure.jpg" style="position:absolute; float:right; right:0px; bottom:0px; z-index:2;">
</div>
</body></html>
