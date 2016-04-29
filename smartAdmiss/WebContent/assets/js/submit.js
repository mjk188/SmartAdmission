

function Submit(){
 var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
 var fname = document.form.Name.value,

  DOB = document.form.DOB.value,
  Sex = document.form.Sex.value,
  Contact = document.form.Contact.value,
 Xmarks = document.form.Xmarks.value,   
 XIImarks = document.form.XIImarks.value,
 Pmarks = document.form.pmarks.value,
 PGmarks = document.form.PGmarks.value,
 if( fname == "" )
   {
     document.form.Name.focus() ;
  document.getElementById("errorBox").innerHTML = "enter the first name";
     return false;
   }
 if( DOB == "" )
   {
     document.form.LastName.focus() ;
   document.getElementById("errorBox").innerHTML = "enter the last name";
     return false;
   }
    
   if (Sex == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }
  if (Contact == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }
  if (Xmarks == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }
  if (XIImarks == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }
  if (Gmarks == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }

  if (PGmarks == "" )
 {
  document.form.Email.focus();
  document.getElementById("errorBox").innerHTML = "enter the email";
  return false;
  }
   <!-- 
}




