<?php

$connection = new mysqli('localhost','root','root','toll','8889');

$cnic = $_POST['cnic'];

$sql = "SELECT * FROM trip WHERE CNIC=".$cnic;

$result = mysqli_query($connection,$sql);

$data = array();

while($row = mysqli_fetch_array($result)){    
    $data[] = $row;
}

print json_encode($data);
?>
