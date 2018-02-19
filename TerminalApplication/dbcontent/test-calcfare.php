<?php

$db = mysqli_connect("localhost","root","root","toll",8889);

$start_time = date("h:i:s");
$trip_date = date("Y-m-d");
$cnic = $_POST['cnic'];
$fairid = $_POST['fair_id'];
$start_booth_id = $_POST['start_booth_ID'];
$plazaid = $_POST['plaza_ID'];
$startLat = $_POST['startLat'];
$startLon = $_POST['startLon'];

$sql = mysqli_query($db, "SELECT Trip_ID FROM trip WHERE CNIC=$cnic AND end_booth_ID IS NULL");

while($row = mysqli_fetch_assoc($sql)){
    $var = $row['Trip_ID'];
}

$result = mysqli_query($db, "INSERT INTO calculate_fare (CFID, Trip_ID, fair_id, startLat, startLon, endLat, endLon, Distance, Total_Fare, CNIC) VALUES (NULL, $var, $fairid, $startLat, $startLon, NULL, NULL, NULL, NULL, $cnic)");

?>

<html>
    <body>
        <form method="post">
            <input name="cnic" type="text"/>
            <input name="fair_id" type="text"/>  
            <input name="plaza_ID" type="text"/>
            <input name="start_booth_ID" type="text"/>
            <input name="startLat" type="number"/>
            <input name="startLon" type="number"/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>