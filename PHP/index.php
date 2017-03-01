<?php
	try{
		$db = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
		//echo "Connected";
	}catch (PDOException $e) {
		//print "Error!: " . $e->getMessage() . "<br/>";
		echo "Not Connected";
		die();
	}

	

	$query = $db->prepare("SELECT * FROM mytable where 1");
	$query->execute();

	if($query->rowCount() > 0){
		$data = $query->fetchAll(PDO::FETCH_ASSOC);

		

		echo json_encode($data);
	}else{
		$json['success'] = 0;
		$json['message'] = 'No Data found';		
		$json['myintro'] = '';

		echo json_encode($json);
	}
?>