<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
        // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
        //including the database connection file
       include_once("config.php");
       
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $address= $_POST['address'];
    $cnic= $_POST['cnic'];
    $phone= $_POST['phone'];
  
	 if($name == '' || $email == '' || $password == '' || $cnic == ''){
	        echo json_encode(array( "status" => "false","message" => "Parameter missing!") );
	 }else{
			 
	        $query= "SELECT * FROM operator WHERE email='$email'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	           echo json_encode(array( "status" => "false","message" => "Username already exist!") );
	        }else{ 
		 	 $query = "INSERT INTO operator (CNIC,full_name,email,password,phone_number,address,plazaId,booth_ID) VALUES ('$cnic','$name','$email','$password','$phone','$address','','')";
			 if(mysqli_query($con,$query)){
			    
			     $query= "SELECT * FROM operator WHERE email='$email'";
	                     $result= mysqli_query($con, $query);
		             $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $row_array['cnic'] = $row['CNIC'];
                                     $row_array['name'] = $row['full_name'];
                                     $row_array['email'] = $row['email'];
                                     $row_array['phone'] = $row['phone_number'];
                                     $row_array['address'] = $row['address'];
                                
                                     array_push($emparray,$row_array);
                                   }
	                     }
			    echo json_encode(array( "status" => "true","message" => "Successfully registered!" , "data" => $emparray) );                
                 
		 	 }else{
		 		 echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
		 	}
	    }
	            mysqli_close($con);
	 }
     } else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
 
 ?>