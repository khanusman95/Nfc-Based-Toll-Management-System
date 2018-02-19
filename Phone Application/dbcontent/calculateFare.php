<?php
include_once("config.php");

//---------------------function start---------------------------------
function distance($lat1, $lon1, $lat2, $lon2, $unit) {

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);

  if ($unit == "K") {
    return ($miles * 1.609344);
  } else if ($unit == "N") {
      return ($miles * 0.8684);
    } else {
        return $miles;
      }
}
//----------------------function end----------------------------------




if($_SERVER['REQUEST_METHOD']=='POST'){
    /*$startPlazaId = $_POST['Start_plazaId'];
    $endPlazaId = $_POST['End_plazaId'];*/
    $vehicle_type = $_POST['vehicle_type'];
    
    $startLat = floatval($_POST['startLat']);
    $endLat = floatval($_POST['endLat']);
    $startLon = floatval($_POST['startLon']);
    $endLon = floatval($_POST['endLon']);
    
    if($vehicle_type == ''){
	        echo json_encode(array( "status" => "false","message" => "Parameter missing!") );
	 }else{
      
        $query = "SELECT fair_amount FROM toll_fare WHERE vehicle_type='$vehicle_type'";
        
        $result= mysqli_query($con, $query);
        
        $rate_per_km;
        
        while($row = mysqli_fetch_assoc($result)){
            $rate_per_km = $row['fair_amount'];
        }
        
        $rpk = floatval($rate_per_km);
        $floatDist = floatval(distance($startLat, $startLon, $endLat, $endLon, "K"));
        //echo $rpk*$floatDist;
        

        /*$emparray = array();
        if(mysqli_num_rows($result) > 0){  
        while ($row = mysqli_fetch_assoc($result)) {
                 $row_array['fair_amount'] = $row['fair_amount'];
                 $row_array['distance'] = $row['distance'];                 

                 array_push($emparray,$row_array);
               }
        }*/
        
        //echo json_encode(($rpk*$floatDist));
        echo json_encode(array( "data" => ($rpk*$floatDist)));

    }
    
}
else{
    echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
}

?>

<html>
    <body>
        <form method="post">
            <!--<input type="number" name="Start_plazaId" placeholder="start">
            <input type="number" name="End_plazaId" placeholder="end">-->
            
            <input type="number" name="vehicle_type" placeholder="vehicle type">
            <input name="startLat" type="text" placeholder="start Lat">
            <input name="startLon" type="text" placeholder="start Lon">
            <input name="endLat" type="text" placeholder="end lat">
            <input name="endLon" type="text" placeholder="end lon">
            
            <button type="submit">Sumbit</button>
        </form>
                
    </body>
</html>