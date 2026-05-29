package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Тесты функциональности класса бургер.

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void shouldInstantiateBurger() {
        assertNotNull("Burger должен успешно создаваться через конструктор по умолчанию", burger);
    }

    //Расчет цены с использованием Mock.

    @Test
    public void shouldCalculatePriceWithMocks() {

        when(bunMock.getPrice()).thenReturn(TestData.PRICE_BUN_DELUXE);
        when(ingredientMock.getPrice()).thenReturn(TestData.PRICE_ING_HEARTY);


        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);

        // Then: Ожидаем сумму: 2 булки и 1 ингредиент
        float expected = TestData.PRICE_BUN_DELUXE * 2 + TestData.PRICE_ING_HEARTY;
        assertEquals("Итоговая цена должна складываться из цен моков", expected, burger.getPrice(), TestData.FLOAT_DELTA);
    }

    @Test
    public void shouldStoreBunReference() {
        Bun testBun = TestData.createBasicBun();
        burger.setBuns(testBun);
        assertEquals("Ссылка на установленную булку должна сохраняться внутри бургера", testBun, burger.bun);
    }

    @Test
    public void shouldAddIngredientToList() {
        Ingredient testIng = TestData.createBisonPatty();
        burger.addIngredient(testIng);
        assertEquals("После добавления ингредиента, список ингредиентов должен содержать 1 элемент", 1, burger.ingredients.size());
    }

    @Test
    public void shouldRemoveIngredientFromList() {
        Ingredient testIng = TestData.createIngredient(IngredientType.FILLING, "temp", 10f);
        burger.addIngredient(testIng);
        burger.removeIngredient(0);
        assertEquals("После удаления единственного ингредиента, список должен стать пустым", 0, burger.ingredients.size());
    }

    @Test
    public void shouldMoveIngredient() {
        Ingredient a = TestData.createBisonPatty();
        Ingredient b = TestData.createZestySauce();
        burger.addIngredient(a);
        burger.addIngredient(b);

        // Меняем местами: [A, B] -> [B, A]
        burger.moveIngredient(0, 1);

        assertEquals("Первый элемент должен стать вторым (B)", b, burger.ingredients.get(0));
        assertEquals("Второй элемент должен стать первым (A)", a, burger.ingredients.get(1));
    }

    // Проверка чека
    @Test
    public void shouldReturnReceiptWithCorrectComponents() {
        Bun testBun = TestData.createDeluxeBun();
        Ingredient testSauce = TestData.createZestySauce();

        burger.setBuns(testBun);
        burger.addIngredient(testSauce);

        String actualReceipt = burger.getReceipt();

        // Формируем ожидаемый чек на основе констант
        String expectedReceipt = String.format(TestData.RECEIPT_FORMAT,
                testBun.getName(),
                testSauce.getType().toString().toLowerCase(),
                testSauce.getName(),
                testBun.getName(),
                burger.getPrice()
        );

        assertEquals("Сгенерированный чек должен совпадать с шаблоном формата", expectedReceipt, actualReceipt);
    }
}