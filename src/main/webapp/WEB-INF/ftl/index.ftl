<#import "spring.ftl" as spring />
<html>
<head>
<title>Hello World.</title>
</head>
<body>
	<h1>${helloworld}</h1>
	<form name="formdata" action="test" method="post">
		<input type="text" name="testData" /> <input type="submit"
			value="Save" />
	</form>
</body>
</html>
