package com.upp.apteka.specifications;

import java.sql.Date;

import com.upp.apteka.utils.repository.HqlSpecification;

/**
 * Delivery specifiacation utils class
 * 
 * @author Solomka
 *
 */
public final class DeliverySpecificationUtils {

	private DeliverySpecificationUtils() {
		throw new RuntimeException();
	}

	/**
	 * find delivery by period
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static HqlSpecification findDeliveryByPeriodAndPharmacy(final Date from, final Date to,
			final Long pharmacyId) {
		return new HqlSpecification() {

			Date fromDate;
			Date toDate;
			Long idPharmacy;

			{
				fromDate = from;
				toDate = to;
				idPharmacy = pharmacyId;

			}

			public String toHql() {

				String hql = "FROM Delivery WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND id_pharmacy="
						+ idPharmacy;
				return hql;
			}
		};
	}

}
