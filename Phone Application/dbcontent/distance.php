<?php

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


$startLat = floatval($_POST['startLat']);
$endLat = floatval($_POST['endLat']);
$startLon = floatval($_POST['startLon']);
$endLon = floatval($_POST['endLon']);

echo distance($startLat,$startLon,$endLat, $endLon, "K")."KM<br>";

$emparray = array();
$row_array['distance'] = distance($startLat,$startLon,$endLat, $endLon, "K");
array_push($emparray,$row_array);
echo json_encode(array( "data" => $emparray));

/*
echo distance(32.9697, -96.80322, 29.46786, -98.53506, "M") . " Miles<br>";
echo distance(32.9697, -96.80322, 29.46786, -98.53506, "K") . " Kilometers<br>";
echo distance(32.9697, -96.80322, 29.46786, -98.53506, "N") . " Nautical Miles<br>";*/

?>


<html>
<body>
    <form method="post">
        <input name="startLat" type="text">
        <input name="startLon" type="text">
        <input name="endLat" type="text">
        <input name="endLon" type="text">
        <button type="submit">Submit</button>
    </form>
    </body>
</html>