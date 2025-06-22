//설명 주석 필요
package com.project.rooton.global.entity;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

// domain/temp/entity/Temp.java (도메인별 엔티티)
package com.project.rooton.domain.temp.entity;

import com.project.rooton.global.entity.BaseTimeEntity;  // import

@Entity
@Table(name = "temp")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Temp extends BaseTimeEntity {  // 상속받기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 예시 필드 추가

    // createdAt, updatedAt은 BaseTimeEntity에서 자동으로 제공
}