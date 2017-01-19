<html>
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

// step 2. get arguments 

$phone_num = $_GET['phone_num'];
$lat = $_GET['lat'];
$lon = $_GET['lon'];
$m_time = $_GET['time'];
$b_per = $_GET['b_per'];

/*
echo $phone_num;
echo '<br>';
echo $lat;
echo '<br>';
echo $lon;
echo '<br>';
echo $m_time;
echo '<br>';
echo $b_per;
*/

if($m_time == NULL) {
	$m_time = date("Y-m-d H:i:s", mktime(date("H")+10, date("i"), date("s"), date("m"), date("d"), date("Y")));
	
}

// step 3. insert data

$sql = "
		INSERT INTO mylocation(
			phone_num,
			lat,
			lon,
			m_time,
			b_per
		)
		values(
			'$phone_num',
			$lat,
			$lon,
			'$m_time',
			$b_per	
		)";

$result = $conn->query($sql);

?>

<?php

$conn->close();

?>

</body>
</html>
