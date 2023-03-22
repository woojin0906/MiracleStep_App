<?php
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $availableStep = 0;

    $statement = mysqli_prepare($con, "SELECT availableStep FROM User WHERE id = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $availableStep);

    $response = array();
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["availableStep"] = $availableStep;
    echo json_encode($response);
?>
