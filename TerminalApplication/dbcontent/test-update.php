<?php

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

$db = mysqli_connect("localhost","root","root","toll",8889);

$end_time = date("h:i:s");
$trip_date = date("Y-m-d");
$cnic = $_POST['cnic'];
$fairid = $_POST['fair_id'];
$end_booth_id = $_POST['end_booth_ID'];
$plazaid = $_POST['plaza_ID'];
$endLat = $_POST['endLat'];
$endLon = $_POST['endLon'];

$result = mysqli_query($db,"UPDATE trip SET end_time='$end_time', end_booth_ID=$end_booth_id WHERE CNIC=$cnic AND end_booth_ID IS NULL");

if($result){
    
$updatecf = mysqli_query($db,"UPDATE calculate_fare SET endLat=$endLat, endLon=$endLon WHERE CNIC=$cnic AND Distance IS NULL");
    
    //$sql = mysqli_query($db, "UPDATE calculate_fare SET endLat = $endLat, endLon = $endLon  WHERE ;");
    $sql = mysqli_query($db, "SELECT * FROM calculate_fare WHERE CNIC = $cnic AND Distance IS NULL");
    while($row = mysqli_fetch_assoc($sql)){
        $startLatitude = $row['startLat'];
        $startLongitude = $row['startLon'];
        $fair_id = $row['fair_id'];
    }
    
$query = "SELECT fair_amount FROM toll_fare WHERE fair_id= $fair_id ";

$result12= mysqli_query($db, $query);

$rate_per_km;

while($row = mysqli_fetch_assoc($result12)){
$rate_per_km = $row['fair_amount'];
}

$rpk = floatval($rate_per_km);
$floatDist = floatval(distance($startLatitude, $startLongitude, $endLat, $endLon, "K"));
$totalFare = ($rpk * $floatDist);
    
    $credit_check = mysqli_query($db, "SELECT * FROM credit WHERE CNIC=$cnic");
    while($row1 = mysqli_fetch_assoc($credit_check)){
        $availableCredit = $row1['credit_amount'];
    }
    
    if($totalFare <= $availableCredit){
        $finalcfUpdate = mysqli_query($db, "UPDATE calculate_fare SET Distance=$floatDist, Total_Fare=$totalFare WHERE CNIC=$cnic AND Distance IS NULL");
    
        $finaltripUpdate = mysqli_query($db, "UPDATE trip SET Distance=$floatDist, Total_Fare = $totalFare WHERE CNIC=$cnic AND Distance IS NULL");
        
        $updateCredit = mysqli_query($db, "UPDATE credit SET credit_amount=($availableCredit-$totalFare) WHERE CNIC=$cnic");
        
        echo json_encode(array("status"=>"success"));
    }else{
        echo json_encode(array("status"=>"failed"));
    }
    
    
    
}

?>

<html>
    <body>
        <form method="post">
            <input name="cnic" type="text"/>                        
            <input name="end_booth_ID" type="text"/>
            <input name="endLat" type="number"/>
            <input name="endLon" type="number"/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>