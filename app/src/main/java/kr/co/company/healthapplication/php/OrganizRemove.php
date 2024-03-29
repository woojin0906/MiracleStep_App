<?php
    /*
      OrganizRemove.php : 기업 회원탈퇴 처리
    */

    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

    $id = isset($_POST["id"]) ? $_POST["id"] : "";

    $statement = mysqli_prepare($con, "DELETE FROM Organization WHERE id = ?;");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);

?>
