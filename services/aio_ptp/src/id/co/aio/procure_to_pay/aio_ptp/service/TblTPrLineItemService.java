/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.TblTPrLineItem;
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqLiStatus;

/**
 * Service object for domain model class {@link TblTPrLineItem}.
 */
public interface TblTPrLineItemService {

    /**
     * Creates a new TblTPrLineItem. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTPrLineItem if any.
     *
     * @param tblTprLineItem Details of the TblTPrLineItem to be created; value cannot be null.
     * @return The newly created TblTPrLineItem.
     */
    TblTPrLineItem create(@Valid TblTPrLineItem tblTprLineItem);


	/**
     * Returns TblTPrLineItem by given id if exists.
     *
     * @param tbltprlineitemId The id of the TblTPrLineItem to get; value cannot be null.
     * @return TblTPrLineItem associated with the given tbltprlineitemId.
	 * @throws EntityNotFoundException If no TblTPrLineItem is found.
     */
    TblTPrLineItem getById(Integer tbltprlineitemId);

    /**
     * Find and return the TblTPrLineItem by given id if exists, returns null otherwise.
     *
     * @param tbltprlineitemId The id of the TblTPrLineItem to get; value cannot be null.
     * @return TblTPrLineItem associated with the given tbltprlineitemId.
     */
    TblTPrLineItem findById(Integer tbltprlineitemId);

	/**
     * Find and return the list of TblTPrLineItems by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tbltprlineitemIds The id's of the TblTPrLineItem to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblTPrLineItems associated with the given tbltprlineitemIds.
     */
    List<TblTPrLineItem> findByMultipleIds(List<Integer> tbltprlineitemIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblTPrLineItem. It replaces all fields of the existing TblTPrLineItem with the given tblTprLineItem.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTPrLineItem if any.
     *
     * @param tblTprLineItem The details of the TblTPrLineItem to be updated; value cannot be null.
     * @return The updated TblTPrLineItem.
     * @throws EntityNotFoundException if no TblTPrLineItem is found with given input.
     */
    TblTPrLineItem update(@Valid TblTPrLineItem tblTprLineItem);


    /**
     * Partially updates the details of an existing TblTPrLineItem. It updates only the
     * fields of the existing TblTPrLineItem which are passed in the tblTprLineItemPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTPrLineItem if any.
     *
     * @param tbltprlineitemId The id of the TblTPrLineItem to be deleted; value cannot be null.
     * @param tblTprLineItemPatch The partial data of TblTPrLineItem which is supposed to be updated; value cannot be null.
     * @return The updated TblTPrLineItem.
     * @throws EntityNotFoundException if no TblTPrLineItem is found with given input.
     */
    TblTPrLineItem partialUpdate(Integer tbltprlineitemId, Map<String, Object> tblTprLineItemPatch);

    /**
     * Deletes an existing TblTPrLineItem with the given id.
     *
     * @param tbltprlineitemId The id of the TblTPrLineItem to be deleted; value cannot be null.
     * @return The deleted TblTPrLineItem.
     * @throws EntityNotFoundException if no TblTPrLineItem found with the given id.
     */
    TblTPrLineItem delete(Integer tbltprlineitemId);

    /**
     * Deletes an existing TblTPrLineItem with the given object.
     *
     * @param tblTprLineItem The instance of the TblTPrLineItem to be deleted; value cannot be null.
     */
    void delete(TblTPrLineItem tblTprLineItem);

    /**
     * Find all TblTPrLineItems matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTPrLineItems.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblTPrLineItem> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblTPrLineItems matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTPrLineItems.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTPrLineItem> findAll(String query, Pageable pageable);

    /**
     * Exports all TblTPrLineItems matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TblTPrLineItems matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TblTPrLineItems in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblTPrLineItem.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);

    /*
     * Returns the associated tblTrfqLiStatuses for given TblTPrLineItem id.
     *
     * @param pliId value of pliId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTRfqLiStatus instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTRfqLiStatus> findAssociatedTblTrfqLiStatuses(Integer pliId, Pageable pageable);

}