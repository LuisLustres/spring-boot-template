package es.nextdigital.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    
    // Buscar por número de tarjeta
    Optional<Card> findByCardNumber(String cardNumber);
    
    // Buscar todas las tarjetas de una cuenta
    List<Card> findByAccount(Account account);
    
    // Buscar tarjetas de un cliente (a través de sus cuentas)
    @Query("SELECT c FROM Card c WHERE c.account.customer = :customer")
    List<Card> findByCustomer(@Param("customer") Customer customer);
    
    // Buscar tarjetas por tipo (débito/crédito)
    List<Card> findByType(CardType type);
    
    // Buscar tarjetas activas
    List<Card> findByIsActiveTrue();
    
    // Buscar tarjetas inactivas
    List<Card> findByIsActiveFalse();
    
    // Buscar tarjetas que requieren cambio de PIN
    List<Card> findByIsActiveTrueAndPinChangedFalse();
    
    // Buscar tarjetas por tipo y estado
    List<Card> findByTypeAndIsActive(CardType type, boolean isActive);
    
    // Verificar si existe un número de tarjeta
    boolean existsByCardNumber(String cardNumber);
    
    // Buscar tarjetas con límite de crédito mayor a X
    List<Card> findByCreditLimitGreaterThan(java.math.BigDecimal limit);
    
    // Buscar tarjetas por fecha de creación (si añades createdAt)
    // List<Card> findByCreatedAtAfter(LocalDateTime date);
    
    // Consulta personalizada para validar tarjeta + PIN (para login en cajero)
    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber AND c.isActive = true")
    Optional<Card> findActiveCardByNumber(@Param("cardNumber") String cardNumber);
    
    // Consulta para encontrar tarjetas bloqueadas o con muchos intentos fallidos
    // (si añades un campo failedAttempts)
    // List<Card> findByFailedAttemptsGreaterThan(int attempts);
    
    // Consulta para estadísticas
    @Query("SELECT COUNT(c) FROM Card c WHERE c.type = :type AND c.isActive = true")
    long countActiveCardsByType(@Param("type") CardType type);
}
