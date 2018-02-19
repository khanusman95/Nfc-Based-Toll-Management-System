<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
  // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
//including the database connection file
       include_once("config.php");
       
        $email = $_POST['email'];
 	$password = $_POST['password'];
 	
	 if( $email == '' || $password == '' ){
	        echo json_encode(array( "status" => "false","message" => "Parameter missing!") );
	 }else{
	 	$query= "SELECT * FROM user WHERE Email='$email' AND Password='$password'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	         $query= "SELECT * FROM user WHERE Email='$email' AND Password='$password'";
	                     $result= mysqli_query($con, $query);
		             $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $row_array['cnic'] = $row['CNIC'];
                                     $row_array['name'] = $row['Full_name'];
                                     $row_array['email'] = $row['Email'];
                                     $row_array['phone'] = $row['phone_Number'];
                                     $row_array['address'] = $row['address'];
                                
                                     array_push($emparray,$row_array);
                                   }
	                     }
	           echo json_encode(array( "status" => "true","message" => "Login successfully!", "data" => $emparray) );
	        }else{ 
	        	echo json_encode(array( "status" => "false","message" => "Invalid username or password!") );
	        }
	         mysqli_close($con);
	 }
	} else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
?>