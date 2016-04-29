<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<body background="assets/img/add2.jpg">

<div>
<h2> Successfully Added Record of ${name} and You have applied for ${course} </h2>

 <form action="getUniversity" name ="success">
 <input type="hidden" value =${name} name="name"/>
 <input type="hidden" value =${lname} name="lname"/>
 <input type="hidden" value =${course} name="course"/>

 <input type ="submit" name="SuccessSubmit" value="Know your University"></input>
 </form>
</div>


</body>
</html>

