package com.example.spring02.service.shop;

import java.util.List;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.model.shop.dto.ProductDTO;

public interface CartService {
	public List<CartDTO> cartMoney();
	public void insert(CartDTO dto); //장바구니추가 
	public  List<CartDTO> listCart(String userid); //장바구니목록
	public void delete(int cart_id); //장바구니 개별삭제
	public void deleteAll(String userid); //장바구니 비우기
	public void update(int cart_id);
	public int sumMoney(String userid); //장바구니 금액합계
	//장바구니에 이미 상품이 담겼는지 확인
	public int countCart(String userid, int product_id); //장바구니 상품 갯수
	//장바구니 수량변경
	public void updateCart(CartDTO dto);  //장바구니 수정
	public void ModifyCart(CartDTO dto);
}
