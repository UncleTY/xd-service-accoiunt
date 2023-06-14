package com.xindong.account.converter;

import java.util.List;

public interface BaseConverter<E, D> {

	E toDo(D d);

	List<E> toDoList(List<D> list);

	D toDto(E d);

	List<D> toDtoList(List<E> list);
}