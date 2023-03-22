<?php
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');

    $UserID = isset($_POST["UserID"]) ? $_POST["UserID"] : "";
    $ListNum = isset($_POST["ListNum"]) ? $_POST["ListNum"] : 0;
    $Checked = isset($_POST["Checked"]) ? $_POST["Checked"] : 0;
    $NowDate = isset($_POST["NowDate"]) ? $_POST["NowDate"] : "";

    $statement = mysqli_prepare($con, "UPDATE CheckList SET checked = ? WHERE userId = ? AND listIndex = ? AND listDate = ?");
    mysqli_stmt_bind_param($statement, "isis", $Checked, $UserID, $ListNum, $NowDate);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
