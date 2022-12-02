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

import id.co.aio.procure_to_pay.aio_ptp.TblTGrConfirmLineItem;

/**
 * Service object for domain model class {@link TblTGrConfirmLineItem}.
 */
public interface TblTGrConfirmLineItemService {

    /**
     * Creates a new TblTGrConfirmLineItem. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTGrConfirmLineItem if any.
     *
     * @param tblTgrConfirmLineItem Details of the TblTGrConfirmLineItem to be created; value cannot be null.
     * @return The newly created TblTGrConfirmLineItem.
     */
    TblTGrConfirmLineItem create(@Valid TblTGrConfirmLineItem tblTgrConfirmLineItem);


	/**
     * Returns TblTGrConfirmLineItem by given id if exists.
     *
     * @param tbltgrconfirmlineitemId The id of the TblTGrConfirmLineItem to get; value cannot be null.
     * @return TblTGrConfirmLineItem associated with the given tbltgrconfirmlineitemId.
	 * @throws EntityNotFoundException If no TblTGrConfirmLineItem is found.
     */
    TblTGrConfirmLineItem getById(Integer tbltgrconfirmlineitemId);

    /**
     * Find and return the TblTGrConfirmLineItem by given id if exists, returns null otherwise.
     *
     * @param tbltgrconfirmlineitemId The id of the TblTGrConfirmLineItem to get; value cannot be null.
     * @return TblTGrConfirmLineItem associated with the given tbltgrconfirmlineitemId.
     */
    TblTGrConfirmLineItem findById(Integer tbltgrconfirmlineitemId);

	/**
     * Find and return the list of TblTGrConfirmLineItems by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tbltgrconfirmlineitemIds The id's of the TblTGrConfirmLineItem to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblTGrConfirmLineItems associated with the given tbltgrconfirmlineitemIds.
     */
    List<TblTGrConfirmLineItem> findByMultipleIds(List<Integer> tbltgrconfirmlineitemIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblTGrConfirmLineItem. It replaces all fields of the existing TblTGrConfirmLineItem with the given tblTgrConfirmLineItem.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTGrConfirmLineItem if any.
     *
     * @param tblTgrConfirmLineItem The details of the TblTGrConfirmLineItem to be updated; value cannot be null.
     * @return The updated TblTGrConfirmLineItem.
     * @throws EntityNotFoundException if no TblTGrConfirmLineItem is found with given input.
     */
    TblTGrConfirmLineItem update(@Valid TblTGrConfirmLineItem tblTgrConfirmLineItem);


    /**
     * Partially updates the details of an existing TblTGrConfirmLineItem. It updates only the
     * fields of the existing TblTGrConfirmLineItem which are passed in the tblTgrConfirmLineItemPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTGrConfirmLineItem if any.
     *
     * @param tbltgrconfirmlineitemId The id of the TblTGrConfirmLineItem to be deleted; value cannot be null.
     * @param tblTgrConfirmLineItemPatch The partial data of TblTGrConfirmLineItem which is supposed to be updated; value cannot be null.
     * @return The updated TblTGrConfirmLineItem.
     * @throws EntityNotFoundException if no TblTGrConfirmLineItem is found with given input.
     */
    TblTGrConfirmLineItem partialUpdate(Integer tbltgrconfirmlineitemId, Map<String, Object> tblTgrConfirmLineItemPatch);

    /**
     * Deletes an existing TblTGrConfirmLineItem with the given id.
     *
     * @param tbltgrconfirmlineitemId The id of the TblTGrConfirmLineItem to be deleted; value cannot be null.
     * @return The deleted TblTGrConfirmLineItem.
     * @throws EntityNotFoundException if no TblTGrConfirmLineItem found with the given id.
     */
    TblTGrConfirmLineItem delete(Integer tbltgrconfirmlineitemId);

    /**
     * Deletes an existing TblTGrConfirmLineItem with the given object.
     *
     * @param tblTgrConfirmLineItem The instance of the TblTGrConfirmLineItem to be deleted; value cannot be null.
     */
    void delete(TblTGrConfirmLineItem tblTgrConfirmLineItem);

    /**
     * Find all TblTGrConfirmLineItems matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTGrConfirmLineItems.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblTGrConfirmLineItem> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblTGrConfirmLineItems matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTGrConfirmLineItems.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTGrConfirmLineItem> findAll(String query, Pageable pageable);

    /**
     * Exports all TblTGrConfirmLineItems matching the given input query to the given exportType format.
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
     * Exports all TblTGrConfirmLineItems matching the given input query to the given exportType format.
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
     * Retrieve the count of the TblTGrConfirmLineItems in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblTGrConfirmLineItem.
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


}