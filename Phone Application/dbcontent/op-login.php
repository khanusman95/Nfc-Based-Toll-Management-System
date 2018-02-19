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
	 	$query= "SELECT * FROM operator WHERE email='$email' AND password='$password'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	         $query= "SELECT * FROM operator WHERE email='$email' AND password='$password'";
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
	           echo json_encode(array( "status" => "true","message" => "Login successfully!", "data" => $emparray) );
                $logEntry = "INSERT INTO accesslog(logId, CNIC, access_time) VALUES('','$cnic','')";
	        }else{ 
	        	echo json_encode(array( "status" => "false","message" => "Invalid username or password!") );
	        }
	         mysqli_close($con);
	 }
	} else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
?>