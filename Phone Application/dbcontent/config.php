<?php
$host="localhost";
$user="root";
$password="root";
$db = "toll";
 
$con = mysqli_connect($host,$user,$password,$db,8889);
 
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }else{  //echo "Connect"; 
    
   }
 
?>