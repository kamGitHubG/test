package com.nominet.dto;

/**
 * DTO object to move data between application and external consumers.
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoteDTO {
	private Long id;
	private String text;

}
