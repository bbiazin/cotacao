<!DOCTYPE html>

<html>
<head>
<style>
body {
	margin: 30px;
}

/*
body * {
	display: block;
}
*/
</style>
</head>

<body>
	<form method="post" action="j_security_check">
		<h3>Área restrita</h3>
		<label>E-mail</label> 
		<br /><input type="text" id="j_username" name="j_username" />
		<br /><label>Senha</label> 
		<br /><input type="password" id="j_password" name="j_password" /> 
		<br /><input type="submit" name="submit" value="Login" />
		<p/>
		<a href="/carregamento/paginas/novaConta.xhtml">Criar uma conta</a> 
		<br /><a href="/carregamento/paginas/esqueciASenha.xhtml">Esqueci minha senha</a> 
	</form>
</body>
</html>