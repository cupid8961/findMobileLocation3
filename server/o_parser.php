<html>
<head>

<script>
function myMap(lat, lon) {
	var mapCanvas = document.getElementById("map");
	var mapOptions = {
		center: new google.maps.LatLng(lat, lon), 
	    zoom: 16
	}
	var map = new google.maps.Map(mapCanvas, mapOptions);
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat,lon),
		map: map,
		title: 'Hello World!'
	});
}
</script>

<script src="https://maps.googleapis.com/maps/api/js?callback=myMap"></script>

</head>
<body>

<?php

// step 1. mysql connection 

$servername = "";
$username = "";
$password = "";
$dbname ="";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);
 
echo "Connected successfully\n";

echo "<br><br>";

?>


<?php

// step 2. get records

?>

<table border=1>

<tr bgcolor="yellow">
	<th>전화번호</th>
	<th>위도</th>
	<th>경도</th>
	<th>시간</th>
	<th>배터리</th>
</tr>

<?php

$records = array();

$sql = "
		SELECT *
		FROM mylocation;
		";

$result = $conn->query($sql);



if($result->num_rows > 0) {
	while($row = $result->fetch_assoc()) {
		$lat = $row['lat'];
		$lon = $row['lon'];
	?>
	<tr>
	<td><?php echo $row['phone_num']; ?></td>
	<td><?php echo $lat; ?></td>
	<td><?php echo $lon; ?></td>
	<td><?php echo $row['m_time']; ?></td>
	<td><?php echo $row['b_per'] . '%'; ?></td>
	</tr>
	<?php
	}
}
?>
</table>

<button onclick="myMap(<?php echo $lat; ?>,<?php echo $lon; ?>)">구글 맵에서 확인하기</button>



<div id="map" style="width:100%;height:500px"></div>

<?php

$conn->close();

?>

</body>
</html>
