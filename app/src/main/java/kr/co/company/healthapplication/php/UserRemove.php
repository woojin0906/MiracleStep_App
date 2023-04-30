<?php
    /*
      UserRemove.php : 사용자 회원탈퇴 처리
    */

    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

    $id = isset($_POST["id"]) ? $_POST["id"] : "";

    $statement = mysqli_prepare($con, "DELETE FROM User WHERE id = ?;");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);

?>
