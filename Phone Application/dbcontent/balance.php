<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
  // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
//including the database connection file
       include_once("config.php");
       
        $cnic = $_POST['cnic']; 	
 	
	 if( $cnic == ''){
	        echo json_encode(array( "status" => "false","message" => "Parameter missing!") );
	 }else{
	 	$query= "SELECT * FROM credit WHERE CNIC='$cnic'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	         $query= "SELECT * FROM credit WHERE CNIC='$cnic'";
	                     $result= mysqli_query($con, $query);
		             $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $row_array['credit_id'] = $row['credit_id'];
                                     $row_array['credit_amount'] = $row['credit_amount'];                                     
                                
                                     array_push($emparray,$row_array);
                                   }
	                     }
	           echo json_encode(array( "status" => "true","message" => $row['credit_amount'], "data" => $emparray) );
	        }else{ 
	        	echo json_encode(array( "status" => "false","message" => "Credit Account Not Available") );
	        }
	         mysqli_close($con);
	 }
	} else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
?>