<?php
  include 'login.php';

  $TABLE_NAME = $_GET["table_name"];
  $result = mysqli_query($link, "SELECT * FROM `".$TABLE_NAME."`");

  $a = [];
  while ($row = mysqli_fetch_assoc($result)) {
    $a[] = $row;
  }
  echo json_encode($a);
?>