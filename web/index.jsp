<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Unity - Log In</title>
    <script type="text/javascript" src="js/wackylogin"></script> 
    <link rel="StyleSheet" href="css/index.css" type="text/css" media="screen" />
</head>
    
<body>
    <div id="logo_box"><img src="photos/unity_logo.png" alt="Unity Logo" width="300" /></div>
    <center><div id="motto">The Academic Social Network!</div></center>
    <div id="login_box" role="main"> 
        <form method="post" action="pages/login.jsp" >
            <fieldset> 
                <label for="username">Username 
                    <span class="ico"><img src="photos/user.png" alt="Username Icon" border="0" /></span>
                </label> 
                <input type="text" name="viewer" id="username" required="autofocus"/> 
                <label for="password">Password 
                    <span class="ico"><img src="photos/pass.png" alt="Password Icon" border="0" /></span>
                </label> 
                <input type="password" name="password" id="password" required="autofocus"/> 
            </fieldset> 
            <fieldset> 
                <span class="password">Not a member? <a href="pages/signup/user_register.html">Register</a></span> 
                <button type='submit'>Login</button> 
            </fieldset> 
        </form> 
    </div> 
</body>
</html>