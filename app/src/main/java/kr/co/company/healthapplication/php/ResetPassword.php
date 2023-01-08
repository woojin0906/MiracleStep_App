<?php

  /* (1) 서버 DB에 연결.*/
  $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
  mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

  /* (2) DB에 저장할 객체 선언. */
  $userPassword = isset($_POST["password"]) ? $_POST["password"] : "";

  /* (3) 현재 DB에 저장된 아이디와 비밀번호를 검색함. */  // ss는 String이 두 개라서 ss가 들어감.
  $statement = mysqli_prepare($con, "UPDATE User SET UserPassword = ?");
  mysqli_stmt_bind_param($statement, "s", $userPassword);
  mysqli_stmt_execute($statement);

  /* (4) 성공 여부 전송. */
  $response = array();
  $response["success"] = true;

  echo json_encode($response);

?>
