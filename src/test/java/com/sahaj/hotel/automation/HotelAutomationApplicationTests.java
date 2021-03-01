package com.sahaj.hotel.automation;

import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_OFF;
import static com.sahaj.hotel.automation.entity.constant.AC_STATE.AC_ON;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_OFF;
import static com.sahaj.hotel.automation.entity.constant.LIGHT_STATE.LIGHT_ON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sahaj.hotel.automation.entity.base.IFloor;
import com.sahaj.hotel.automation.entity.base.IHotel;
import com.sahaj.hotel.automation.motion.controller.AutomationController;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class HotelAutomationApplicationTests {

	@Autowired
	IHotel hotel;

	@Autowired
	AutomationController automationController;
	
	// main corridors: 2
	// sub corridors: 4
	// allowed consumption: 70
	// initial total consumption: 70
	@Test
	@Order(1)
	void test1() {
		// test initial state which is
		// main corridor AC and Light should be ON
		// sub corridor AC should be ON and Light should be OFF
		
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(70, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_OFF);			
			assertEquals(corridor.getACState(), AC_ON);			
		});		
	}
	
	@Test
	@Order(2)
	void test2() {
		// no motion in 1st sub-corridor
		// main corridor AC and Light should be ON
		// sub corridor AC should be ON and Light should be OFF
		
		automationController.motion(1, 1, false);
		
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(70, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_OFF);			
			assertEquals(corridor.getACState(), AC_ON);			
		});		
	}
	
	@Test
	@Order(3)
	void test3() throws Exception {
		// movement in 1st sub corridor
		// total consumption: 75 after Light is ON
		// expected consumption: 65
	automationController.motion(1, 1, true);
	// expected output
		// main corridor AC and Light should be ON
		// 1st sub-corridor AC and light should be ON
		// other sub-corridor's light should be OFF
		// one of the sub-corridors AC from 2,3,4 should be OFF
	Thread.sleep(1000);
	
	IFloor firstFloor = hotel.getFloors().stream().findFirst().get();

	assertEquals(65, firstFloor.getTotalConsumption());
	
	firstFloor.getMainCorridors().forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_ON);			
		assertEquals(corridor.getACState(), AC_ON);			
	});
	
	firstFloor.getSubCorridors().stream()
		.filter(el->el.getNumber() == 1)
		.forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

	AtomicInteger expectedCount = new AtomicInteger();
	firstFloor.getSubCorridors().stream()
	.filter(el->el.getNumber() != 1)
	.forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_OFF);			
		if(corridor.getACState().equals(AC_ON))
			expectedCount.incrementAndGet();
	});
	
	assertEquals(expectedCount.get(), 2);

	}
	
	@Test
	@Order(4)
	void test4() throws Exception {
		// movement in 1st sub corridor
		// total consumption: 65 after Light is ON
		// expected consumption: 65
	automationController.motion(1, 1, true);
	// expected output
		// main corridor AC and Light should be ON
		// 1st sub-corridor AC and light should be ON
		// other sub-corridor's light should be OFF
		// one of the sub-corridors AC from 2,3,4 should be OFF
	Thread.sleep(1000);
	
	IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
	
	assertEquals(65, firstFloor.getTotalConsumption());
	
	firstFloor.getMainCorridors().forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_ON);			
		assertEquals(corridor.getACState(), AC_ON);			
	});
	
	firstFloor.getSubCorridors().stream()
		.filter(el->el.getNumber() == 1)
		.forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

	AtomicInteger expectedCount = new AtomicInteger();
	firstFloor.getSubCorridors().stream()
	.filter(el->el.getNumber() != 1)
	.forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_OFF);			
		if(corridor.getACState().equals(AC_ON))
			expectedCount.incrementAndGet();
	});
	
	assertEquals(expectedCount.get(), 2);

	}

	@Test
	@Order(5)
	void test5() throws Exception {
		// movement in 2nd sub corridor
		// total consumption: 70 || 80
		// expected consumption: 70
	automationController.motion(1, 2, true);
	// expected output
		// main corridor AC and Light should be ON
		// 1st, 2nd sub-corridor AC and light should be ON
		// other sub-corridor's light should be OFF
		// one of the sub-corridors AC from 3,4 should be OFF
	Thread.sleep(1000);
	
	IFloor firstFloor = hotel.getFloors().stream().findFirst().get();

	assertEquals(70, firstFloor.getTotalConsumption());

	firstFloor.getMainCorridors().forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_ON);			
		assertEquals(corridor.getACState(), AC_ON);			
	});
	
	firstFloor.getSubCorridors().stream()
		.filter(el->el.getNumber() == 1 || el.getNumber() == 2)
		.forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

	AtomicInteger expectedCount = new AtomicInteger();
	firstFloor.getSubCorridors().stream()
	.filter(el->el.getNumber() != 1 && el.getNumber() != 2)
	.forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_OFF);			
		if(corridor.getACState().equals(AC_ON))
			expectedCount.incrementAndGet();
	});
	
	assertEquals(expectedCount.get(), 1);

	}

	@Test
	@Order(6)
	void test6() throws Exception {
		// movement in 3rd sub corridor
		// total consumption: 75 || 85
		// expected consumption: 75
	automationController.motion(1, 3, true);
	// expected output
		// main corridor AC and Light should be ON
		// 1st, 2nd, 3rd sub-corridor AC and light should be ON
		// other sub-corridor's light and AC should be OFF
	Thread.sleep(1000);
	
	IFloor firstFloor = hotel.getFloors().stream().findFirst().get();

	assertEquals(75, firstFloor.getTotalConsumption());

	firstFloor.getMainCorridors().forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_ON);			
		assertEquals(corridor.getACState(), AC_ON);			
	});
	
	firstFloor.getSubCorridors().stream()
		.filter(el->el.getNumber() == 1 || el.getNumber() == 2 || el.getNumber() == 3)
		.forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

	firstFloor.getSubCorridors().stream()
	.filter(el->el.getNumber() != 1 && el.getNumber() != 2 && el.getNumber() != 3)
	.forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_OFF);			
		assertEquals(corridor.getACState(), AC_OFF);			
	});
	


	}

	@Test
	@Order(7)
	void test7() throws Exception {
		// movement in 4th sub corridor
		// total consumption: 90
		// expected consumption: 90
	automationController.motion(1, 4, true);
	// expected output
		// main corridor AC and Light should be ON
		// 1st, 2nd, 3rd, 4th sub-corridor AC and light should be ON
	Thread.sleep(1000);
	
	IFloor firstFloor = hotel.getFloors().stream().findFirst().get();

	assertEquals(90, firstFloor.getTotalConsumption());

	firstFloor.getMainCorridors().forEach(corridor-> {
		assertEquals(corridor.getLightState(), LIGHT_ON);			
		assertEquals(corridor.getACState(), AC_ON);			
	});
	
	firstFloor.getSubCorridors().stream()
		.forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});
	}

	@Test
	@Order(8)
	void test8() {
		// no motion in 1st sub-corridor
		// total consumption: 85
		// expected consumption: 75
		automationController.motion(1, 1, false);
		
		// expected output
		// main corridor AC and Light should be ON
		// 2nd, 3rd, 4th sub-corridor AC and light should be ON
		// 1st sub-corridor light and AC should be off 
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(75, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 2 || el.getNumber() == 3 || el.getNumber() == 4)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_ON);			
				assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 1)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_OFF);			
				assertEquals(corridor.getACState(), AC_OFF);			
		});
	}
	
	@Test
	@Order(9)
	void test9() {
		// no motion in 2nd sub-corridor
		// total consumption: 70
		// expected consumption: 70
		automationController.motion(1, 2, false);
		
		// expected output
		// main corridor AC and Light should be ON
		// 2nd, 3rd, 4th sub-corridor AC should be ON
		// 2nd light should be off
		// 1st sub-corridor light and AC should be off 
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(70, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 3 || el.getNumber() == 4)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_ON);			
				assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 2)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_OFF);			
				assertEquals(corridor.getACState(), AC_ON);			
		});

		
		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 1)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_OFF);			
				assertEquals(corridor.getACState(), AC_OFF);			
		});
	}
	
	@Test
	@Order(10)
	void test10() {
		// no motion in 3rd sub-corridor
		// total consumption: 65
		// expected consumption: 65
		automationController.motion(1, 3, false);
		
		// expected output
		// main corridor AC and Light should be ON
		// 2nd, 3rd, 4th sub-corridor AC should be ON and 
		// only 4th sub-corridorlight should be ON
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(65, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().forEach(corridor-> {
			if(corridor.getNumber() == 2 || corridor.getNumber() == 3 || corridor.getNumber() == 4)
				assertEquals(corridor.getACState(), AC_ON);			
			else 
				assertEquals(corridor.getACState(), AC_OFF);			
		});	

		firstFloor.getSubCorridors().stream()
			.forEach(corridor-> {
				if(corridor.getNumber() == 4)
					assertEquals(corridor.getLightState(), LIGHT_ON);			
				else 
					assertEquals(corridor.getLightState(), LIGHT_OFF);			
			});

		
		firstFloor.getSubCorridors().stream()
			.filter(el->el.getNumber() == 1)
			.forEach(corridor-> {
				assertEquals(corridor.getLightState(), LIGHT_OFF);			
				assertEquals(corridor.getACState(), AC_OFF);			
		});
	}
	
	@Test
	@Order(11)
	void test11() {
		// no motion in 4th sub-corridor
		// total consumption: 60
		// expected consumption: 70
		automationController.motion(1, 4, false);
		
		// expected output
		// main corridor AC and Light should be ON
		// 2nd, 3rd, 4th sub-corridor AC should be ON and 
		// only 4th sub-corridorlight should be ON
		IFloor firstFloor = hotel.getFloors().stream().findFirst().get();
		
		assertEquals(70, firstFloor.getTotalConsumption());
		
		firstFloor.getMainCorridors().forEach(corridor-> {
			assertEquals(corridor.getLightState(), LIGHT_ON);			
			assertEquals(corridor.getACState(), AC_ON);			
		});

		firstFloor.getSubCorridors().forEach(corridor-> {
				assertEquals(corridor.getACState(), AC_ON);			
				assertEquals(corridor.getLightState(), LIGHT_OFF);			
		});	

	}
}
