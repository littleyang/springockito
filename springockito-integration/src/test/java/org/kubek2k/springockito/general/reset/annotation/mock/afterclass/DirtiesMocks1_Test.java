package org.kubek2k.springockito.general.reset.annotation.mock.afterclass;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.experimental.DirtiesMocks;
import org.kubek2k.springockito.annotations.experimental.junit.AbstractJUnit4SpringockitoContextTests;
import org.kubek2k.springockito.general.reset.MockedBean;
import org.kubek2k.tools.Ordered;
import org.kubek2k.tools.OrderedSpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(OrderedSpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/general/reset/annotation/mock/afterclass/context.xml"})
@DirtiesMocks(classMode = DirtiesMocks.ClassMode.AFTER_CLASS)
public class DirtiesMocks1_Test extends AbstractJUnit4SpringockitoContextTests {

    @Resource(name = "mockedAndResetAfterClass")
    @ReplaceWithMock
    MockedBean firstBean;

    @Ordered(1)
    @Test
    public void recordSomeBehaviour() {
        TestSuiteToImposeTestsOrder.insideSuite.assume();

        given(firstBean.returnString("ala123"))
                .willReturn("bob123");

        String returnedString = firstBean.returnString("ala123");

        verify(firstBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

    @Ordered(2)
    @Test
    public void verifyBehaviourStillNotReset() {
        TestSuiteToImposeTestsOrder.insideSuite.assume();

        String returnedString = firstBean.returnString("ala123");

        verify(firstBean, times(2))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }
}
