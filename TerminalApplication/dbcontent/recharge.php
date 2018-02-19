<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
  // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
//including the database connection file
       include_once("config.php");                         
       
       $cnic = $_POST['cnic']; 
       $amount = $_POST['amount'];
       //$tax = ($amount * 25)/100;
       $query = "SELECT * FROM tax";
        $result = mysqli_query($con, $query);

        while($row = mysqli_fetch_assoc($result)){
            $tax = $row['rate'];
        }
       $net = $amount-$tax;
 	
	 if( $cnic == ''){
	        echo json_encode(array( "status" => "false","message" => "cnic missing!") );
	 }elseif ( $amount == ''){
         echo json_encode(array( "status" => "false","message" => "amount missing!") );
     }
       else{
	 	$query= "SELECT * FROM credit WHERE CNIC='$cnic'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	            $query= "UPDATE credit SET credit_amount=credit_amount+'$net' WHERE CNIC='$cnic'";
	            $result= mysqli_query($con, $query);
                
                $balanceQuery = "SELECT credit_amount FROM credit WHERE CNIC='$cnic'";
                $balanceUpdate = mysqli_query($con, $balanceQuery);
                
                echo json_encode(mysqli_fetch_object($balanceUpdate));
		         
	           //echo json_encode(array( "status" => "true") );
	        }else{ 
	        	echo json_encode(array( "message" => "Entry not Found") );
	        }
	         mysqli_close($con);
	 }
	} else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );            
	}
?>